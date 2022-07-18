<template>
  <v-app>
    <v-main class="pt-0">
    <v-app-bar app dark color="deep-purple" class="box">
      <v-toolbar-title
        ><v-icon left>mdi-lightning-bolt-outline</v-icon>Blitz
        <v-icon>mdi-lightning-bolt-outline</v-icon></v-toolbar-title
      >
      <v-spacer></v-spacer>
      <router-link class="mx-2" :to="'/profile/'+$route.params.Email" tag="button">
        <v-btn large text rounded>
          <v-icon left>mdi-account-box</v-icon>
          Profile
        </v-btn>
      </router-link>
      <router-link class="mx-2" to="/" tag="button">
          <v-btn large text rounded>
            <v-icon left>mdi-logout</v-icon>
            Log out
          </v-btn>
        </router-link>
    </v-app-bar>
    <v-row>
      <v-col cols="2"  class="pt-0 pr-0" >
    <v-card app height="655" width="200">
      <v-navigation-drawer
        absolute
        dark
        color="deep-purple"
        src=""
        width="100%"
        permanent
      >
        <v-list>
          <v-list-item v-for="item in navigationBar" :key="item.text" link :to="item.route+$route.params.Email">
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{ item.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-card>
      </v-col>
      <v-col cols="10" class="pa-0">
        <v-toolbar dark color="deep-purple">
          <v-toolbar-title>MultiSearch Selection</v-toolbar-title>
            <v-btn icon @click="manipulate"><v-icon>mdi-magnify</v-icon></v-btn>
        <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
          <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
        <v-list-item v-for="(prop, index) in searchProps" :key="index">
          <v-list-item-content>
            <label>{{ prop.name }}</label>
            <v-text-field v-model="prop.query"></v-text-field>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-content>
            <label>Priority</label>
            <v-radio-group row v-model="Priority" class="mx-5 my-5">
              <v-radio v-for="i in priorities" :key="i" :label="i"></v-radio>
            </v-radio-group>
          </v-list-item-content>
        </v-list-item>
        </v-list>
        </v-menu>


        <v-toolbar-title class="ml-10">Sort</v-toolbar-title>
        <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-on="on" v-bind="attrs"><v-icon>mdi-sort</v-icon></v-btn>
        </template>
        <v-list>
        <v-radio-group v-model="sortMode">
          <v-radio v-for="i in sortProps" :key="i" :label="i" class="ma-3 pa-3"></v-radio>
        </v-radio-group>
        </v-list>
        </v-menu>

        

        <v-toolbar-title class="ml-10">Filter From All</v-toolbar-title>
        <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-on="on" v-bind="attrs"><v-icon>mdi-filter</v-icon></v-btn>
        </template>
        <v-list>
        <v-list-item>
          <v-list-item-content>
            <label>Sender</label>
            <v-text-field v-model="filterSender"></v-text-field>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-content>
            <label>Subject</label>
            <v-text-field v-model="filterSubject"></v-text-field>
          </v-list-item-content>
        </v-list-item>
        </v-list>
        </v-menu>

        <v-btn color="#311B92" class="mx-7" @click="applyFilter">ApplyFilter</v-btn>


        <v-btn icon class="mx-8" @click="refresh(page)"> <v-icon>mdi-refresh</v-icon>
        </v-btn>

        <v-btn icon class="mx-8" @click="forward">forward
        </v-btn>

        <v-btn icon class="mx-8" @click="backward">backward
        </v-btn>


      </v-toolbar>
      
      <v-layout row>
        <v-btn block height="45px" v-for="(email,i) in emails" :key="i" v-on:click=openEmail(i) >
          {{email.Subject}}
          <v-btn class="warning mx-10" icon><v-icon @click="remove(i)">mdi-delete</v-icon></v-btn>
        </v-btn>
      </v-layout>
      </v-col>
    </v-row>
    </v-main>
  </v-app>
</template>

<script>
import axios from 'axios';
export default {
    mounted(){
        if(this.$store.state.AllowAccess!=this.$route.params.Email){
          this.$router.push('/signin/')
        }
        this.usersEmail=this.$route.params.Email;
        axios.get("http://localhost:8080/getCurrentPath").then(resp=>{
          this.Path=resp.data+this.Path
          this.refresh(1)
        })  
    },
    methods: {
      remove(index){
        this.Path += '\\' + this.emails[index].id + '.json'
        console.log(this.Path);
        let formData = new FormData()
        formData.append('Path',this.Path)
        axios.post("http://localhost:8080/delete",formData)
        .then(() => {
          this.$router.push("/sent/"+this.$route.params.Email)
        })
      },
      forward(){
        this.page++
        this.refresh(this.page);
      },
      backward(){
        if (this.page > 1) {
          this.page--
        }
        this.refresh(this.page);
      },
      openEmail(i){
        this.$store.state.Mails=this.emails[i]
        this.$router.push('/mailview/'+this.$route.params.Email)
      },
      refresh(index){
        let formData = new FormData();
        formData.append('Path',this.Path)
        this.page = index
        formData.append('Page',this.page)
        axios.post('http://localhost:8080/display',formData)
        .then((response) => {
          this.emails = response.data;
          if (this.emails.length == 0) {
            if (this.page > 1) {
              this.refresh(--this.page)
            }
            else if (this.page == 1) {
              this.refresh(this.page)
            }
          }
        })
        .catch(() => {
          alert('Could not get emails')
        })
      },
      manipulate(){
        let formData = new FormData();
        formData.append('Path',this.Path)
        formData.append('Page',this.page)
        let Arr = []
        for (let i = 0; i < this.searchProps.length; i++) {
          if (this.searchProps[i].query != '') {
            Arr.push(['Search',this.searchProps[i].name,this.searchProps[i].query])
          }
        }
        if (this.sortMode != 5) {
          Arr.push(['Sort',this.sortMode,null])
        }
        formData.append('Arr',Arr)
        formData.append('Page',this.page)
        axios.post('http://localhost:8080/manipulate',formData)
        .then((response) => {
          this.emails = response.data;
        })
        .catch(() => {
          alert('Could not get emails')
        })

      },
      applyFilter(){
        let formData = new FormData();
        formData.append('Path',this.Path)
        formData.append('Page',this.page)
        let type = ''
        let query = []
        if (this.filterSender != '' && this.filterSubject != '') {
          type = 'And'
          query = [this.filterSender, this.filterSubject]
        }
        else if (this.filterSubject != '') {
          type = 'Subject'
          query = [this.filterSubject]
        }
        else if (this.filterSender != '') {
          type = 'Sender'
          query = [this.filterSender]
        }
        formData.append('Type',type)
        formData.append('Query',query)
        axios.post('http://localhost:8080/filter',formData)
        .then((response) => {
          this.emails = response.data;
        })
        .catch(() => {
          alert('Could not get emails')
        })

      }
    },
    data(){ 
      return{
      emails: [],
      navigationBar: [
        {icon: "mdi-account-supervisor-circle",text: "Contacts",route: "/contacts/"},
        { icon: "mdi-inbox-arrow-down", text: "Inbox", route: "/inbox/"},
        { icon: "mdi-email-check", text: "Sent", route: "/sent/" },
        { icon: "mdi-email-newsletter", text: "Draft", route: "/draft/" },
        { icon: "mdi-trash-can", text: "Trash", route: "/trash/" },
        { icon: "mdi-email-plus", text: "Compose", route: "/compose/" }
      ],
      Path: "\\src\\main\\java\\demo\\blitz\\Data\\Users\\" + this.$route.params.Email + '\\Sent',
      searchProps: [{name : 'Sender', query: ''},{name : 'Subject', query: ''},
      {name : 'Body', query: ''},
      {name : 'Date', query: ''},
      {name : 'Attachments', query: ''}],
      sortProps: ['Sender','Subject','Body','Date','Priority','NAN'],
      sortMode: 5,
      priorities: ['Urgent','High','Medium','Low','NAN'],
      Priority: 4,
      page: 1,
      filterSender: '',
      filterSubject: ''
      }
    }
};
</script>

<style>

</style>