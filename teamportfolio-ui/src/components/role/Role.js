import React, { useState, useEffect } from "react";
import RoleDataService from "../../services/RoleService";

const Role = props => {
  const initialRoleState = {
    id: null,
    title: "",
    description: ""
  };
  const [currentRole, setCurrentRole] = useState(initialRoleState);
  const [message, setMessage] = useState("");

  const getRole = id => {
    RoleDataService.get(id)
      .then(response => {
        console.log(response.data);
        setCurrentRole(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getRole(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentRole({ ...currentRole});
  };

  const updateRole = () => {
    RoleDataService.update(currentRole.id, currentRole)
      .then(response => {
        console.log(response.data);
        setMessage("The role was updated successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deleteRole = () => {
    RoleDataService.remove(currentRole.id)
      .then(response => {
        console.log(response.data);
        props.history.push("/roles");
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div>
      {currentRole ? (
        <div className="edit-form">
          <h4>Role</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                name="title"
                value={currentRole.title}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                name="description"
                value={currentRole.description}
                onChange={handleInputChange}
              />
            </div>
          </form>

          <button className="btn btn-danger" onClick={deleteRole}>
            Delete
          </button>

          <button type="submit" className="btn btn-success" onClick={updateRole}>
            Update
          </button>
          
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Role...</p>
        </div>
      )}
    </div>
  );
};

export default Role;