import React, { useState, useEffect } from "react";
import SkillDataService from "../../services/SkillService";

const Skill = props => {
  const initialSkillState = {
    id: null,
    name: "",
    description: ""
  };
  const [currentSkill, setCurrentSkill] = useState(initialSkillState);
  const [message, setMessage] = useState("");

  const getSkill = id => {
    SkillDataService.get(id)
      .then(response => {
        console.log(response.data);
        setCurrentSkill(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    getSkill(props.match.params.id);
  }, [props.match.params.id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrentSkill({ ...currentSkill});
  };

  const updateSkill = () => {
    SkillDataService.update(currentSkill.id, currentSkill)
      .then(response => {
        console.log(response.data);
        setMessage("The skill was updated successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deleteSkill = () => {
    SkillDataService.remove(currentSkill.id)
      .then(response => {
        console.log(response.data);
        props.history.push("/skills");
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div>
      {currentSkill ? (
        <div className="edit-form">
          <h4>Skill</h4>
          <form>
            <div className="form-group">
              <label htmlFor="name">Title</label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={currentSkill.name}
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
                value={currentSkill.description}
                onChange={handleInputChange}
              />
            </div>
          </form>

          <button className="btn btn-danger" onClick={deleteSkill}>
            Delete
          </button>

          <button type="submit" className="btn btn-success" onClick={updateSkill}>
            Update
          </button>
          
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Skill...</p>
        </div>
      )}
    </div>
  );
};

export default Skill;