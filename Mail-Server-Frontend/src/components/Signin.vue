<template>
  <v-app>
    <v-app-bar
      class="animation-area banner-text box-area"
      app
      dark
      color="deep-purple"
    >
      <v-toolbar-title
        ><v-icon left>mdi-lightning-bolt-outline</v-icon>Blitz
        <v-icon>mdi-lightning-bolt-outline</v-icon></v-toolbar-title
      >
      <v-spacer></v-spacer>
      <router-link class="mx-2" to="/" tag="button">
        <v-btn text rounded> <v-icon left>mdi-home</v-icon> Home </v-btn>
      </router-link>
      <router-link class="mx-2" to="/signin" tag="button">
        <v-btn text rounded> <v-icon left>mdi-login</v-icon> Sign in </v-btn>
      </router-link>
      <router-link class="mx-2" to="/signup" tag="button">
        <v-btn text rounded> <v-icon left>mdi-creation</v-icon> Sign Up </v-btn>
      </router-link>
    </v-app-bar>

    <div class="banner-text"></div>
    <div class="animation-area">
      <ul class="box-area">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ul>
    </div>
    <v-content class="centered">
      <v-card width="500" class="mx-auto my-9">
        <v-card-title>Sign in</v-card-title>
        <v-card-text style="color:#B22222 ;font-size:20px" >{{ErrorMessage}}</v-card-text>
        <v-card-text>
          <v-text-field id="EmailSignIn"
            color="deep-purple"
            label="Username"
            prepend-icon="mdi-account-circle"
          />
          <v-text-field id="PasswordSignIn"
            color="deep-purple"
            label="Password"
            :type="showPassword ? 'text' : 'password'"
            prepend-icon="mdi-lock"
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPassword = !showPassword"
          />
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <router-link class="mx-2" to="/signup" tag="button">
            <v-btn dark rounded color="deep-purple"> Sign Up </v-btn>
          </router-link>
          <v-spacer></v-spacer>
            <v-btn dark rounded color="deep-purple" v-on:click="login()"> Login </v-btn>
        </v-card-actions>
      </v-card>
    </v-content>

    <!-- footer bar -->
    <!-- <template>
      <v-footer padless app color="deep-purple">
        <v-col class="text-center" cols="12"> </v-col>
      </v-footer>
    </template> -->
  </v-app>
</template>

<script>
import axios from 'axios'
import Mails from "./Mails.vue";
export default {
  component:{
      Mails
    },
  mounted(){
    this.$store.state.AllowAccess=""
  },
  data() {
    return {
      ErrorMessage:"",
      showPassword: false,
    };
  },
  methods:{
    login(){
        const password = document.getElementById("PasswordSignIn").value
        const email = document.getElementById("EmailSignIn").value
        if(email=="" || email==null){
          this.ErrorMessage = "Username's field is empty"
          return
        }
        else if(password=="" || password==null){
          this.ErrorMessage = "Password's field is empty"
          return
        }
        axios.post("http://localhost:8080/verifyUser",{
          Email:email,
          Password:password
        }).then(resp => {
          if(resp.data===true){
            this.$router.push('/mails/'+email)
          }
          else{
            this.ErrorMessage = "invalid username or password"
          }
        }) 
    },
  }
};
</script>

<style>
.centered {
  position: fixed;
  top: 40%;
  left: 50%;
  /* bring your own prefixes */
  transform: translate(-50%, -50%);
}
</style>