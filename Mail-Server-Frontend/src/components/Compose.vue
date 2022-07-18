<template>
  
  <v-card max-width="600" class="mx-auto my-10">
    <v-system-bar color="deep-purple" class="title white--text px-2 py-6">New message</v-system-bar>
    
    <v-row class="px-6 py-3" align="center">
      <span class="mr-4">To</span>
      <v-combobox deletable-chips label="Receivers" v-model="Receivers" multiple chips></v-combobox>
    </v-row>

    <template>
     <v-container
      class="px-0"
      fluid
     >
    <v-radio-group row v-model="Priority" class="mx-5 my-5">
      <v-radio v-for="i in priorities" :key="i.name" :label="i.name" :color="i.color"></v-radio>
    </v-radio-group>
  </v-container>
  </template>
    
    <v-text-field v-model="Subject" class="mx-5 my-5"
      label="Subject" single-line>
    </v-text-field>

    <v-textarea v-model="Body" class="mx-5 my-5" single-line label="Body">
    </v-textarea>

    <v-file-input v-model="currentFiles" class="mx-5 my-5" chips counter multiple full-width
    show-size truncate-length="20" @change="addFiles">
    </v-file-input>

    <v-list class="mr-10 ml-8 my-5 pa-5" max-width="400">
      <v-list-item-group  v-model="model">
        <v-list-item v-for="(file, i) in Attachments" :key="i">
          <v-list-item-content>
            <v-list-item-title v-text="file.name"></v-list-item-title>
          </v-list-item-content>
          <v-list-item-icon><v-icon v-text="icon" @click="deleteFile(i)"></v-icon></v-list-item-icon>
        </v-list-item>
      </v-list-item-group>
    </v-list>

    <v-row>
      <v-btn class="mx-10 my-5 py-5 purple" dark @click="validateReceivers">send</v-btn>
      <v-btn class="mx-10 my-5 py-5 purple" dark @click="saveToDraft">savetodraft</v-btn>
    </v-row>
  </v-card>
</template>

<script>

import axios from "axios";
export default {
    mounted(){
        if(this.$store.state.AllowAccess!=this.$route.params.Email){
          this.$router.push('/signin/')
        }
        this.usersEmail=this.$route.params.Email;
    },
    data() {
        return {
            name: "Compose",
            priorities:[{name: 'Urgent',color: '#FF0000'},{name: 'High',color: '#2126E3'},
            {name: 'Medium',color: '#21E32C'},{name: 'Low',color: '#800080'}],
            Sender: '',
            Receivers: [],
            Priority: '2',
            Subject: '',
            Body: '',
            Date: '',
            currentFiles: [],
            Attachments: [],
            Email: {},
            invalidReceivers: [],
            model: 1,
            deleted: false,
            icon: 'mdi-delete',
            fileToDelete: undefined
        }
    },
    methods: {
        validateReceivers() {
          if (this.Receivers.length == 0){
            alert("You have to enter at least one receiver!")
          } 
          else if (!this.validate()){
            alert("You must enter valid address!")
          }
          else {
            let date = new Date();
            this.Date += date.getFullYear() + '/';
            this.Date += date.getMonth() + '/';
            this.Date += date.getDay();
            let formData = new FormData();
            for (let i = 0; i < this.Attachments.length; i++) {
                formData.append('files[]', this.Attachments[i])
            }
            
            this.Attachments = []
            this.Sender = this.$route.params.Email;
            this.Email = {Sender: this.Sender, Receivers: this.Receivers, Priority: this.Priority,
            Subject: this.Subject, Body: this.Body,Date: this.Date, Attachments: this.Attachments};
            formData.append('Sender',this.Sender)
            formData.append('Receivers',this.Receivers)
            formData.append('Priority',this.Priority)
            formData.append('Subject',this.Subject)
            formData.append('Body',this.Body)
            formData.append('Date',this.Date)
            formData.append('Attachments',this.Attachments)
           
           axios.post('http://localhost:8080/compose',formData,
           {headers: {'Content-Type': 'multipart/form-data'}})
           .then((response) => {
             if (response.data.length == 0) {
                alert("Message Sent Successfully.")    
             }
             else{
                alert("Couldn't Send Message to : " + response.data.toString())
             }       
           })
           .catch(() => {
               alert("Error Occured because Attachments size is too Large.")
           })
          }
        },
        validate(){
          let address = '@blitz.com';
          for (let i = 0; i < this.Receivers.length; i++) {
            let str = this.Receivers[i];
            if (str.endsWith(address) && str.length > address.length){
              return true  
            }
          }
          return false
        }
        ,
        addFiles() {
          for (let i = 0; i < this.currentFiles.length; i++)
            this.Attachments.push(this.currentFiles[i]);
        },
        deleteFile(index){
          this.Attachments.splice(index,1);
        },
        saveToDraft(){
            if (this.Receivers.length == 0){
            alert("You have to enter at least one receiver!")
          } 
          else if (!this.validate()){
            alert("You must enter valid address!")
          }
          else{
            this.Date = new Date().toLocaleString().toString();
            let formData = new FormData();
            for (let i = 0; i < this.Attachments.length; i++) {
                formData.append('files[]', this.Attachments[i])
            }
            
            this.Attachments = []
            this.Sender = this.$route.params.Email
            this.Email = {Sender: this.Sender, Receivers: this.Receivers, Priority: this.Priority,
            Subject: this.Subject, Body: this.Body,Date: this.Date, Attachments: this.Attachments};
            formData.append('Sender',this.Sender)
            formData.append('Receivers',this.Receivers)
            formData.append('Priority',this.Priority)
            formData.append('Subject',this.Subject)
            formData.append('Body',this.Body)
            formData.append('Date',this.Date)
            formData.append('Attachments',this.Attachments)
           
           axios.post('http://localhost:8080/draft',formData,
           {headers: {'Content-Type': 'multipart/form-data'}})
           .then(() => {        
              alert("Message Saved to Draft.")})
           .catch(() => {
               alert("Error Occured because Attachments size is too Large.")
           })
          }
            
        }
    },
}
</script>