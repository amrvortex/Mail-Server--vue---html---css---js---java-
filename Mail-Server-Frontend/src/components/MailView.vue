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
      <v-col cols="2"  class="pt-0">
    <v-card app  height="655" width="200">
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
      <v-row>
          <v-card-title>
              {{Subject}}
          </v-card-title>
          <v-card-text>
              <p>Priority: {{Prio[Priority]}}</p>
              Date:{{MessageDate}}
          </v-card-text>
          <v-card-text>
              <strong>{{Sender}}  </strong>
              <br/>
              <span v-for="receiver in Receivers" :key='receiver'>{{receiver}}  </span>
          </v-card-text>
          <v-card-text style="word-wrap:break-word; " class="text-wrap">
           <strong>Body:</strong><br/>
           {{Body}}
           </v-card-text>
           <v-card-text>
            <strong>Attachements:</strong><br/>
          <v-btn v-for="(attachement,i) in attachmentName" :key=i v-on:click="downloadAttachement(i)">
              {{attachement}}
          </v-btn>
           </v-card-text>
      </v-row>
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
        this.Subject=this.$store.state.Mails.Subject
        this.Sender=this.$store.state.Mails.Sender
        this.Priority=this.$store.state.Mails.Priority
        this.Receivers=this.$store.state.Mails.Receivers
        this.Attachement=this.$store.state.Mails.Attachments
        this.Body=this.$store.state.Mails.Body
        this.MessageDate=this.$store.state.Mails.Date
        for(let i=0;i<this.Attachement.length;i++){
            this.attachmentName.push(this.Attachement[i].substring(this.Attachement[i].lastIndexOf("\\") + 1))
        }
    },
    data: () => ({
        Prio:[
          "Urgent",
          "High",
          "Medium",
          "Low"],
        Subject:"",
        Body:"",
        MessageDate:"",
        Priority:"",
        Attachement:[],
        Receivers:[],
        Sender:"",
        attachmentName:[],
      navigationBar: [
        { icon: "mdi-account-supervisor-circle",text: "Contatcts",route: "/contacts/"},
        { icon: "mdi-inbox-arrow-down", text: "Inbox", route: "/inbox/"},
        { icon: "mdi-email-check", text: "Sent", route: "/sent/" },
        { icon: "mdi-email-newsletter", text: "Draft", route: "/draft/" },
        { icon: "mdi-trash-can", text: "Trash", route: "/trash/" },
        { icon: "mdi-email-plus", text: "Compose", route: "/compose/" }
      ],
      }),
      methods:{
          downloadAttachement(i){
              axios.post("http://localhost:8080/download/",{
                Attachement:this.Attachement[i]
              },{responseType: 'arraybuffer'}).then(resp=>{
              var a = window.document.createElement('a');
              a.href = window.URL.createObjectURL(new Blob([resp.data], { type: 'application/octet-stream' }));
              a.download = this.attachmentName[i];

              // Append anchor to body.
              document.body.appendChild(a)
              a.click();

              // Remove anchor from body
              document.body.removeChild(a)
              })
                
          }
      }
};
</script>

<style>

</style>