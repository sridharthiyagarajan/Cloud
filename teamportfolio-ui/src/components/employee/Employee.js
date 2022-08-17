import React, { useState, useEffect } from "react";
import EmployeeDataService from "../../services/EmployeeService";

const Employee = props => {
  const initialEmployeeState = {
    id: null,
    employeeReferenceNumber: "",
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    roleId: ""
  };
  const [currentEmployee, setCurrentEmployee] = useState(initialEmployeeState);
  const [message, setMessage] = useState("");

  const getEmployee = id => {
    EmployeeDataService.get(id)
      .then(response => {
        console.log(response.data);
        setCurrentEmployee(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getEmployee(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentEmployee({ ...currentEmployee});
  };

  const updateEmployee = () => {
    EmployeeDataService.update(currentEmployee.id, currentEmployee)
      .then(response => {
        console.log(response.data);
        setMessage("The employee was updated successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deleteEmployee = () => {
    EmployeeDataService.remove(currentEmployee.id)
      .then(response => {
        console.log(response.data);
        props.history.push("/employees");
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div>
      {currentEmployee ? (
        <div className="edit-form">
          <h4>Employee</h4>
          <form>
            <div className="form-group">
              <label htmlFor="employeeReferenceNumber">Employee Reference Number</label>
              <input
                type="text"
                className="form-control"
                id="employeeReferenceNumber"
                name="employeeReferenceNumber"
                value={currentEmployee.employeeReferenceNumber}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="firstName">First Name</label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                name="firstName"
                value={currentEmployee.firstName}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name</label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                name="lastName"
                value={currentEmployee.lastName}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="text"
                className="form-control"
                id="email"
                name="email"
                value={currentEmployee.email}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="phone">Phone</label>
              <input
                type="text"
                className="form-control"
                id="phone"
                name="phone"
                value={currentEmployee.phone}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="roleId">Role ID</label>
              <input
                type="text"
                className="form-control"
                id="roleId"
                name="roleId"
                value={currentEmployee.roleId}
                onChange={handleInputChange}
              />
            </div>
          </form>

          <button className="btn btn-danger" onClick={deleteEmployee}>
            Delete
          </button>

          <button type="submit" className="btn btn-success" onClick={updateEmployee}>
            Update
          </button>
          
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Employee...</p>
        </div>
      )}
    </div>
  );
};

export default Employee;