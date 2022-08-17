import React, {Component, useEffect} from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import AddRole from "./components/role/AddRole.js";
import Role from "./components/role/Role.js";
import RolesList from "./components/role/RolesList.js";
import AddSkill from "./components/skill/AddSkill.js";
import Skill from "./components/skill/Skill.js";
import SkillsList from "./components/skill/SkillsList.js";
import AddProject from "./components/project/AddProject.js";
import Project from "./components/project/Project.js";
import ProjectsList from "./components/project/ProjectsList.js";
import AddEmployee from "./components/employee/AddEmployee.js";
import Employee from "./components/employee/Employee.js";
import EmployeesList from "./components/employee/EmployeesList.js";

function App() {

  const logo = require('./sap.png');

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-white">
        <img alt='SAP' src={String(logo)} style={{ width: 100 }} />
        <h4><strong>Team Portfolio</strong></h4>
      </nav>

      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/roles"} className="nav-link">
              Roles
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/addRole"} className="nav-link">
              Add Role
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/skills"} className="nav-link">
              Skills
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/addSkill"} className="nav-link">
              Add Skill
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/projects"} className="nav-link">
              Projects
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/addProject"} className="nav-link">
              Add Project
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/employees"} className="nav-link">
              Employees
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/addEmployee"} className="nav-link">
              Add Employee
            </Link>
          </li>
        </div>
      </nav>

      <div className="container mt-3">
        <Switch>
          <Route exact path={["/", "/roles"]} component={RolesList} />
          <Route exact path="/addRole" component={AddRole} />
          <Route path="/roles/:id" component={Role} />
          <Route exact path={["/skills"]} component={SkillsList} />
          <Route exact path="/addSkill" component={AddSkill} />
          <Route path="/skills/:id" component={Skill} />
          <Route exact path={["/projects"]} component={ProjectsList} />
          <Route exact path="/addProject" component={AddProject} />
          <Route path="/projects/:id" component={Project} />
          <Route exact path={["/employees"]} component={EmployeesList} />
          <Route exact path="/addEmployee" component={AddEmployee} />
          <Route path="/employees/:id" component={Employee} />
        </Switch>
      </div>
    </div>
  );
}

export default App;