<template>
  <v-app>
  </v-app>
</template>

<script>
import axios from 'axios'
export default {
  mounted(){
    axios.post("http://localhost:8080/allowUserAccess",{
      Email:this.$route.params.Email
    }).then(resp=>{
      if(resp.data==true){
        this.$store.state.AllowAccess=this.$route.params.Email
        this.$router.push('/inbox/'+this.$route.params.Email)
      }
      else{
        this.$router.push('/signin/')
      }
    })
  },
  data: () => ({
    allowAccess:true
  }),
};
</script>

<style>

</style>