import Button from "../UI/Button";

import classes from './Footer.module.css';  

const Footer = () => {


  return (
    <div className={classes.wrapper}>
      <Button type={"add-new-task-btn"}/>
    </div>
  );
};

export default Footer;