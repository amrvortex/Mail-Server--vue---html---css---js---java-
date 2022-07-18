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
    <v-content class="centeredd">
      <v-card width="500">
        <v-card-title>Sign Up</v-card-title>
        <v-card-text style="color:#B22222 ;font-size:20px" >{{ErrorMessage}}</v-card-text>
        <v-card-text>
          <v-text-field id="userNameSignUp"
            color="deep-purple"
            label="Username"
            prepend-icon="mdi-account-circle"
          />
          <div>
            <v-text-field id="EmailSignUp"
              class="textarea1"
              color="deep-purple"
              label="E-mail"
              prepend-icon="mdi-card-account-mail"
            />
          </div>
          <div>
            <v-text-field
              class="textarea2 font-bold no-underline"
              disabled
              label="@blitz.com"
            />
          </div>
          <v-text-field id="passwordSignUp"
            color="deep-purple"
            label="Password"
            :type="showPassword ? 'text' : 'password'"
            prepend-icon="mdi-lock"
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPassword = !showPassword"
          />
          <v-text-field id="reEnterPasswordSignUp"
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
          <router-link class="mx-2" to="/signin" tag="button">
            <v-btn dark color="deep-purple" rounded> Sign in </v-btn>
          </router-link>
          <v-spacer></v-spacer>
          <v-btn dark color="deep-purple" rounded v-on:click="CreateNewAccount()">Create account</v-btn>
        </v-card-actions>
      </v-card>
    </v-content>

    <!-- footer bar -->
    <!-- <template>
      <v-footer height="30"  padless app color="deep-purple">
        <v-col cols="24"> </v-col>
      </v-footer>
    </template> -->
  </v-app>
</template>

<script>
import axios from 'axios'
export default {
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
    CreateNewAccount(){
      const username = document.getElementById("userNameSignUp").value
      const email = document.getElementById("EmailSignUp").value
      const password = document.getElementById("passwordSignUp").value
      const reEnterPassword = document.getElementById("reEnterPasswordSignUp").value
      if(username=="" || email=="" || password=="" || reEnterPassword==""){
        this.ErrorMessage="Please fill all fields"
        return
      }
      else if(username.length<3 || username.length>32){
        this.ErrorMessage="please enter a user name between 3 and 32 character"
        return
      }
      else if(email.includes("@") || email.includes(".com") || email.includes(" ")){
        this.ErrorMessage="Emails cannot include @, .com or spaces"
      }
      else if(password.length<8 || password.length>32){
        this.ErrorMessage="please enter a password between 8 and 32 character"
      }
      else if(password!=reEnterPassword){
        this.ErrorMessage="Passwords didnt match"
      }
      else{
        axios.post("http://localhost:8080/createUser",{
          Name:username,
          Email:email+"@blitz.com",
          Password:password
        }).then(resp=>{
          if(!resp.data){
            this.ErrorMessage="Email already taken"
          }
          else{
            alert("Email created succefully")
            this.$router.push('/signin')
          }
        })
      }
    }
  },
};
</script>

<style>
.centeredd {
  position: fixed;
  top: 50%;
  left: 50%;
  /* bring your own prefixes */
  transform: translate(-50%, -50%);
}
div {
  display: inline-block;
}
.textarea1 {
  width: 310px;
}
.textarea2 {
  width: 100px;
  margin-left: 20px;
}
</style>