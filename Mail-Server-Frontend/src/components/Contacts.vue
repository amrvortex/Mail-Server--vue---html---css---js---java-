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
      <v-col cols="2"  class="pt-0 pr-0" >
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
      <v-col cols="10" class="pa-0">
        <v-btn block height="45px" v-for="(Contact,i) in Contacts" :key="i" >
          {{Contact}}
        </v-btn>
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
        axios.get("http://localhost:8080/displayContacts/"+this.usersEmail).then(resp=>{
          for(let i=0;i<resp.data.length;i++){
            this.Contacts.push(resp.data[i])
          }
        })
    },
    data: () => ({
      usersEmail:"",
      Contacts: [],
      navigationBar: [
        {icon: "mdi-account-supervisor-circle",text: "Contacts",route: "/contacts/"},
        { icon: "mdi-inbox-arrow-down", text: "Inbox", route: "/inbox/"},
        { icon: "mdi-email-check", text: "Sent", route: "/sent/" },
        { icon: "mdi-email-newsletter", text: "Draft", route: "/draft/" },
        { icon: "mdi-trash-can", text: "Trash", route: "/trash/" },
        { icon: "mdi-email-plus", text: "Compose", route: "/compose/" }
      ],
      }),
    methods:{
      refresh(){
        axios.get("http://localhost:8080/displayContacts/"+this.usersEmail).then(resp=>{
          for(let i=0;i<resp.data.length;i++){
            this.Contacts.push(resp.data[i])
          }
        })
      }
    }
};
</script>

<style>

</style>