import React, {useState, useEffect, useMemo, useRef} from "react";
import EmployeeDataService from "../../services/EmployeeService";
import { useTable } from "react-table";

const EmployeesList = (props) => {

    const [employees, setEmployees] = useState([]);
    const [searchFirstName, setSearchFirstName]  = useState("");
    const employeesRef = useRef();

    employeesRef.current = employees;

    useEffect (() => {
        retrieveEmployees();
    }, []);

    const onChangeSearchFirstName = (e) => {
        const searchFirstName = e.target.value;
        setSearchFirstName(searchFirstName);
    };

    const retrieveEmployees = () => {
        EmployeeDataService.getAll()
        .then((response) => {
            setEmployees(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };
        
    const refreshList = () => {
        retrieveEmployees();
    };

    const findByFirstName = () => {
        EmployeeDataService.findByFirstName(searchFirstName)
        .then((response) => {
            setEmployees(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };

    const openEmployee = (rowIndex) => {
        const id = employeesRef.current[rowIndex].id;
        props.history.push("/employees/" + id);
    };

    const columns = useMemo(
        () => [
            {
                Header: "Employee Reference Number",
                accessor: "employeeReferenceNumber",
            },
            {
              Header: "FirstName",
              accessor: "firstName",
            },
            {
              Header: "LastName",
              accessor: "lastName",
            },
            {
              Header: "Email",
              accessor: "email",
            },
            {
                Header: "Phone",
                accessor: "phone",
            },
            {
              Header: "Role ID",
              accessor: "roleId",
            },
            {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return(
                        <div>
                            <span onClick={() => openEmployee(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteEmployee(rowIdx)}>
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
        data: employees,
    });

    const deleteEmployee = (rowIndex) => {
        const id = employeesRef.current[rowIndex].id;
    
        EmployeeDataService.remove(id)
          .then((response) => {
            props.history.push("/employees");
    
            let newEmployees = [...employeesRef.current];
            newEmployees.splice(rowIndex, 1);
    
            setEmployees(newEmployees);
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
                placeholder="Search by firstName"
                value={searchFirstName}
                onChange={onChangeSearchFirstName}
              />
              <div className="input-group-append">
                <button
                  className="btn btn-outline-secondary"
                  type="button"
                  onClick={findByFirstName}
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

export default EmployeesList;