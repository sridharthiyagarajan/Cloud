import React, {useState} from "react";
import EmployeeDataService from "../../services/EmployeeService";

const AddEmployee = () => {

    const initialEmployeeState = {
        id : null, 
        employeeReferenceNumber: "",
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        roleId: ""
    };

    const [employee, setEmployee] = useState(initialEmployeeState);
    const[submitted, setSubmitted] = useState(false);

    const handleInputChange = event => {
        const {name, value} = event.target;
        setEmployee({...employee, [name]:value});
    }

    const saveEmployee = () => {
        var data = {
            employeeReferenceNumber: employee.employeeReferenceNumber,
            firstName: employee.firstName,
            lastName: employee.lastName,
            email: employee.email,
            phone: employee.phone,
            roleId: employee.roleId
        }

        EmployeeDataService.create(data)
        .then(response => {
            setEmployee({
                employeeReferenceNumber: employee.employeeReferenceNumber,
                firstName: employee.firstName,
                lastName: employee.lastName,
                email: employee.email,
                phone: employee.phone,
                roleId: employee.roleId
            });
            setSubmitted(true);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const newEmployee = () => {
        setEmployee(initialEmployeeState);
        setSubmitted(false);
    };

    return (

        <div className="submit-form">
            {
                submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={newEmployee}>Add</button>
                </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="employeeReferenceNumber">Employee Reference Number</label>
                            <input type="text" className="form-control" id="employeeReferenceNumber" required value={employee.employeeReferenceNumber} onChange={handleInputChange} name="employeeReferenceNumber" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="firstName">First Name</label>
                            <input type="text" className="form-control" id="firstName" required value={employee.firstName} onChange={handleInputChange} name="firstName" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="lastName">Title</label>
                            <input type="text" className="form-control" id="lastName" required value={employee.lastName} onChange={handleInputChange} name="lastName" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <input type="text" className="form-control" id="email" required value={employee.email} onChange={handleInputChange} name="email" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="phone">Phone</label>
                            <input type="text" className="form-control" id="phone" required value={employee.phone} onChange={handleInputChange} name="phone" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="roleId">Role ID</label>
                            <input type="text" className="form-control" id="roleId" required value={employee.roleId} onChange={handleInputChange} name="roleId" />
                        </div>

                        <button onClick={saveEmployee} className="btn btn-success">Submit</button>
                    </div>
                )
            }
        </div>
    );
};

export default AddEmployee;