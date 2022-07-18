<template>
  <v-app>
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
      <v-col cols="2"  class="pt-0" >
    <v-card app class="centeredd" height="655" width="200">
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
      <v-col cols="10 pt-0">
        <v-content class="centered">
          <v-card width="500">
            <v-card-title>Add Contact</v-card-title>
            <v-card-text style="color:#B22222 ;font-size:15px" >{{ErrorMessage}}</v-card-text>
            <v-card-text>
              <v-text-field id="AddContact"
                color="deep-purple"
                label="Contact Email"
                prepend-icon="mdi-account-circle"
              />
            </v-card-text>

            <v-divider></v-divider>
            <v-card-actions>
              <router-link class="mx-2" :to="'/profile/'+$route.params.Email" tag="button">
                <v-btn dark color="deep-purple" rounded>
                  <v-icon> mdi-cancel</v-icon> Cancel
                </v-btn>
              </router-link>
              <v-spacer></v-spacer>
              <v-btn dark color="deep-purple" rounded v-on:click="addContact()">
                <v-icon> mdi-content-save</v-icon>Add Contact</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-content>
      </v-col>
    </v-row>
  </v-app>
</template>

<script>
import axios from 'axios'
export default {
    mounted(){
        if(this.$store.state.AllowAccess!=this.$route.params.Email){
          this.$router.push('/signin/')
        }
        this.usersEmail=this.$route.params.Email;
    },
    data: () => ({
        showPassword:false,
        ErrorMessage:"Enter users Email",
        usersEmail:"",
        navigationBar: [
            {icon: "mdi-account-supervisor-circle",text: "Contatcts",route: "/contacts/"},
            { icon: "mdi-inbox-arrow-down", text: "Inbox", route: "/inbox/"},
            { icon: "mdi-email-check", text: "Sent", route: "/sent/" },
            { icon: "mdi-email-newsletter", text: "Draft", route: "/draft/" },
            { icon: "mdi-trash-can", text: "Trash", route: "/trash/" },
            { icon: "mdi-email-plus", text: "Compose", route: "/compose/" }
      ],
      }),
    methods:{
    addContact(){
        const ContactName = document.getElementById("AddContact").value
        if(ContactName==""){
            this.ErrorMessage="please fill the 'Add Contact' field or cancel"
            return
        }
        axios.post("http://localhost:8080/addContact",{
            Email:this.$route.params.Email,
            Contact:ContactName
        }).then(resp=>{
            if(resp.data){
                alert("Contact have been added");
                this.$route.push("/profile")
            }
            else{
                alert("The contact you added doesnt exist :(")
            }
        })
    }
    }
};
</script>

<style>

</style>