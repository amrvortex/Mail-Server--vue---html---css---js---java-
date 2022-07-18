import Vue from "vue";
import Vuex from 'vuex';
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import VueRouter from "vue-router";
import Routes from './routes'
import "./assets/style.css";

Vue.use(VueRouter);
Vue.use(Vuex)
const router = new VueRouter({
  routes: Routes
});
Vue.config.productionTip = false;
export const store = new Vuex.Store({
  state:{
    AllowAccess:"",
    Mail:[],
  }
})
new Vue({
  store:store,
  vuetify,
  render: (h) => h(App),
  router:router
}).$mount("#app");
