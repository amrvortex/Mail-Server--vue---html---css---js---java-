import Signup from "./components/Signup.vue";
import Signin from "./components/Signin.vue";
import Home from "./components/Home.vue";
import Profile from "./components/Profile.vue";
import Compose from "./components/Compose.vue";
import Contacts from "./components/Contacts.vue";
import Inbox from "./components/Inbox.vue";
import Mails from "./components/Mails.vue";
import Sent from "./components/Sent.vue";
import Draft from "./components/Draft.vue";
import Trash from "./components/Trash.vue";
import Editprofile from "./components/Editprofile.vue";
import MailView from "./components/MailView.vue";
import AddContact from "./components/AddContact.vue"

export default [
    { path: "/", component: Home },
    { path: "/signup", component: Signup },
    { path: "/signin", component: Signin },
    { path: "/mails/:Email", component: Mails },
    { path: "/profile/:Email", component: Profile },
    { path: "/compose/:Email", component: Compose },
    { path: "/contacts/:Email", component: Contacts },
    { path: "/inbox/:Email", component: Inbox },
    { path: "/sent/:Email", component: Sent },
    { path: "/draft/:Email", component: Draft },
    { path: "/trash/:Email", component: Trash },
    { path: "/mailview/:Email", component: MailView },
    { path: "/addcontact/:Email", component: AddContact },
    { path: "/editprofile/:Email", component: Editprofile },
];