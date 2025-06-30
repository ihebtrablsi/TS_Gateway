//DEV - EMULATOR
const baseUrl = 'http://10.0.2.2:8080/api';
const baseUrlReportManagerMS = 'http://10.0.2.2:8081/api';

//DEV - PHYSICAL DEVICE
// const baseUrl = 'http://192.168.137.1:8080/api';
// const baseUrlReportManagerMS = 'http://192.168.137.1:8081/api';


//PROD
// const baseUrl = 'http://169.255.70.44/api';
// const baseUrlReportManagerMS = 'http://169.255.70.44/services/reportmanage/api';

const loginLink = '/';
const requestReportsLink = '/reportRequestsList';
const requestReportsDetailsLink = '/reportRequestsDetails';
const filesDefinitionFormLink = '/reportRequestsFilesDefForm';
const reportTabMenuLink = '/reportTabMenu';
const filterLink = '/filterRapport';
const welcomeLink = '/welcome';
const profileLink = '/profile';
const passwordLink = '/password';
const notificationsLink = '/notifs';


//ID IN CODE ( TO BE REFACTORED LATER )
const IN_PROGRESS_ID = 1251;
const CONFIRM_CANCEL_ID = 4401;
const TO_VALIDATION = 2202;

const CURRENT_VERSION = '0.8';


//VERSION IN WELCOME SCREEN AND RAPPORT REQ REPO
