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
            <v-card-title>Edit Profile</v-card-title>
            <v-card-text style="color:#B22222 ;font-size:20px" >{{ErrorMessage}}</v-card-text>
            <v-card-text>
              <v-text-field id="nameModify"
                color="deep-purple"
                label="Name"
                prepend-icon="mdi-account-circle"
              />
              <v-text-field id="oldPasswordModify"
                color="deep-purple"
                label="Old Password"
                :type="showPassword ? 'text' : 'password'"
                prepend-icon="mdi-lock"
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPassword = !showPassword"
              />
              <v-text-field id="newPasswordModify"
                color="deep-purple"
                label="New Password"
                :type="showPassword ? 'text' : 'password'"
                prepend-icon="mdi-lock"
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPassword = !showPassword"
              />
              <v-text-field id="reEnterPasswordModify"
                color="deep-purple"
                label="Re-Password"
                :type="showPassword ? 'text' : 'password'"
                prepend-icon="mdi-lock"
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPassword = !showPassword"
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
              <v-btn dark color="deep-purple" rounded v-on:click="modifyUser()">
                <v-icon> mdi-content-save</v-icon>Save Changes</v-btn
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
        ErrorMessage:"PS:fill Name,passwords or both to change them accordingly or leave all to make no change ^_^",
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
        modifyUser(){
            const name = document.getElementById("nameModify").value
            const oldPassword = document.getElementById("oldPasswordModify").value
            const newPassword = document.getElementById("newPasswordModify").value
            const reEnterPassword = document.getElementById("reEnterPasswordModify").value
            let username=null
            let password=null

            if(name!=""){
                if(name.length<3 || name.length>32){
                    this.ErrorMessage="please enter a user name between 3 and 32 character"
                    return
                }
                else{
                    username=name
                }
            }
            if(oldPassword!="" || newPassword!="" || reEnterPassword!=""){
                if(oldPassword=="" || newPassword=="" || reEnterPassword==""){
                    this.ErrorMessage="please fill all 3 password fields to change your password"
                    return
                }
                else if(newPassword.length<8 || newPassword.length>32){
                    this.ErrorMessage="please enter a password between 8 and 32 character"
                    return
                }
                else if(newPassword!=reEnterPassword){
                    this.ErrorMessage="new and reEnter passwords doesnt match"
                    return
                }
                else{
                    password=newPassword
                }
            }
            if(name=="" && oldPassword==""){
                this.$router.push('/profile/'+this.$route.params.Email)
                return
            }
            if(oldPassword!=""){
            axios.post("http://localhost:8080/verifyUser",{
                Email:this.$route.params.Email,
                Password:oldPassword,
            }).then(resp=>{
                if(!resp.data){
                    this.ErrorMessage="old password doesnt match"
                    return
                }
                else{
                    axios.post("http://localhost:8080/modifyUser",{
                        Email:this.$route.params.Email,
                        Name:username,
                        Password:password
                    }).then(
                        alert("Changes have been saved succesfully"),
                        this.$router.push('/profile/'+this.$route.params.Email)
                    )
                }
            })
        }
        else{
            axios.post("http://localhost:8080/modifyUser",{
                Email:this.$route.params.Email,
                Name:username,
                Password:password
            }).then(
                alert("Changes have been saved succesfully"),
                this.$router.push('/profile/'+this.$route.params.Email)
            )
        }
        }
    }
};
</script>

<style>

</style>