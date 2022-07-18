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
        <v-content>
          <v-card class="mx-auto" app max-width="404">
            <v-card-text>
              <v-img
                src="https://miro.medium.com/max/1200/0*UyEKhRBaINAtNNiB.png"
                height="100px"
              ></v-img>

              <v-card-title class="text-lg font-bold"> User Name:</v-card-title>
              <v-card-subtitle class="text-lg"> {{username}}</v-card-subtitle>

              <v-card-title class="text-lg font-bold"> E-mail: </v-card-title>
              <v-card-subtitle class="text-lg"> {{Email}}</v-card-subtitle>

              <v-card-title class="text-lg font-bold"> Password: </v-card-title>
              <v-card-subtitle class="text-lg"> {{Password}}</v-card-subtitle>

              <v-card-title class="text-lg font-bold">
                Other E-mails:
              </v-card-title>
              <v-card-subtitle class="text-lg"></v-card-subtitle>
              <v-card-actions>
              <v-row align="center" justify="center">
                <v-flex align="center" justify="center">
                <router-link class="mx-2" :to="'/editprofile/'+$route.params.Email" tag="button">
                <v-btn color="deep-purple" class="white--text full-width"
                  ><v-icon class="mr-2">mdi-account-edit</v-icon> Edit
                  Profile</v-btn
                ></router-link
              >
              <router-link class="mx-2" :to="'/addcontact/'+$route.params.Email" tag="button">
                <v-btn color="deep-purple" class="white--text full-width"
                  ><v-icon class="mr-2">mdi-account-edit</v-icon> Add Contact</v-btn
                ></router-link
              >
                </v-flex>
              <v-btn color="red darken-4" class="white--text " v-on:click="DeleteUser()"
                ><v-icon left> mdi-cancel</v-icon> Delete Account
                <v-icon right> mdi-cancel</v-icon></v-btn
              > 
              </v-row>
            </v-card-actions>
            </v-card-text>
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
        axios.get("http://localhost:8080/getUsersInfo/"+this.$route.params.Email).then(resp=>{
            this.username=resp.data.Name
            this.Email=resp.data.Email
            this.Password=resp.data.Password
        })
    },
    data: () => ({
        username:"",
        Email:"",
        Password:"",
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
        DeleteUser(){
          var deleteMyAccount = confirm("Are you sure you want to delete this account?\nthis cannot be undone\npress ok to delete the account or cancel to go back")
          if(deleteMyAccount){
            axios.delete("http://localhost:8080/"+this.$route.params.Email).then(
              alert("Your account have been deleted"),
              this.$router.push('/signin/')
            )
          }
        }
      }
};
</script>

<style>

</style>
