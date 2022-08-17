import http from "../http-common";

const getAll = () => {
    return http.get("/skills");
}

const get = (id) => {
    return http.get("/skills/" + id);
  };
  
  const create = (data) => {
    return http.post("/skills", data);
  };
  
  const update = (id, data) => {
    return http.put("/skills/" + id, data);
  };
  
  const remove = (id) => {
    return http.delete("/skills/" + id);
  };
  
  const SkillService = {
    getAll,
    get,
    create,
    update,
    remove,
  };

  export default SkillService;