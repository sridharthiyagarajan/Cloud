import React, {useState} from "react";
import RoleDataService from "../../services/RoleService";

const AddRole = () => {

    const initialRoleState = {
        id : null,
        title: "",
        description: ""
    };

    const [role, setRole] = useState(initialRoleState);
    const[submitted, setSubmitted] = useState(false);

    const handleInputChange = event => {
        const {name, value} = event.target;
        setRole({...role, [name]:value});
    }

    const saveRole = () => {
        var data = {
            title: role.title,
            description: role.description
        }

        RoleDataService.create(data)
        .then(response => {
            setRole({
                title : response.data.title,
                description : response.data.description
            });
            setSubmitted(true);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const newRole = () => {
        setRole(initialRoleState);
        setSubmitted(false);
    };

    return (

        <div className="submit-form">
            {
                submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={newRole}>Add</button>
                </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="title">Title</label>
                            <input type="text" className="form-control" id="title" required value={role.title} onChange={handleInputChange} name="title" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <input type="text" className="form-control" id="description" required value={role.description} onChange={handleInputChange} name="description" />
                        </div>

                        <button onClick={saveRole} className="btn btn-success">Submit</button>
                    </div>
                )
            }
        </div>
    );
};

export default AddRole;