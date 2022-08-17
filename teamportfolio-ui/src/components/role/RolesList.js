import React, {useState, useEffect, useMemo, useRef} from "react";
import RoleDataService from "../../services/RoleService";
import { useTable } from "react-table";

const RolesList = (props) => {

    const [roles, setRoles] = useState([]);
    const [searchTitle, setSearchTitle]  = useState("");
    const rolesRef = useRef();

    rolesRef.current = roles;

    useEffect (() => {
        retrieveRoles();
    }, []);

    const onChangeSearchTitle = (e) => {
        const searchTitle = e.target.value;
        setSearchTitle(searchTitle);
    };

    const retrieveRoles = () => {
        RoleDataService.getAll()
        .then((response) => {
            setRoles(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };
        
    const refreshList = () => {
        retrieveRoles();
    };

    const findByTitle = () => {
        RoleDataService.findByTitle(searchTitle)
        .then((response) => {
            setRoles(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };

    const openRole = (rowIndex) => {
        const id = rolesRef.current[rowIndex].id;
        props.history.push("/roles/" + id);
    };

    const columns = useMemo(
        () => [
            {
                Header: "Title",
                accessor: "title",
            },
            {
                Header: "Description",
                accessor: "description",
            },
            {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return(
                        <div>
                            <span onClick={() => openRole(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteRole(rowIdx)}>
                                <i className="fas fa-trash action"></i>
                            </span>
                        </div>
                    );
                },   
            },
        ],
        []
    );

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow
    } = useTable({
        columns,
        data: roles,
    });

    const deleteRole = (rowIndex) => {
        const id = rolesRef.current[rowIndex].id;
    
        RoleDataService.remove(id)
          .then((response) => {
            props.history.push("/roles");
    
            let newRoles = [...rolesRef.current];
            newRoles.splice(rowIndex, 1);
    
            setRoles(newRoles);
          })
          .catch((e) => {
            console.log(e);
          });
      };

    return (
        <div className="list row">
          <div className="col-md-8">
            <div className="input-group mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Search by title"
                value={searchTitle}
                onChange={onChangeSearchTitle}
              />
              <div className="input-group-append">
                <button
                  className="btn btn-outline-secondary"
                  type="button"
                  onClick={findByTitle}
                >
                  Search
                </button>
              </div>
            </div>
          </div>
          <div className="col-md-12 list">
            <table
              className="table table-striped table-bordered"
              {...getTableProps()}
            >
              <thead>
                {headerGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th {...column.getHeaderProps()}>
                        {column.render("Header")}
                      </th>
                    ))}
                  </tr>
                ))}
              </thead>
              <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                  prepareRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => {
                        return (
                          <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                        );
                      })}
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      );
};

export default RolesList;