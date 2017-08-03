package it.polito.dp2.NFFG.lab3.test3;

import it.polito.dp2.NFFG.lab3.ServiceException;

/**
 * A simple administration interface for managing the maintenance state of the service
 *
 */
public interface MaintenanceClient {

	/**
	 * Puts the service(s) in maintenance state
	 * @throws ServiceException thrown if the service is currently not available or any other error occurs when trying to interact with the service
	 */
	public void setMaintenance()
			throws ServiceException;

	/**
	 * Puts the service(s) in normal state
	 * @throws ServiceException thrown if the service is currently not available or any other error occurs when trying to interact with the service
	 */
	public void setRunning()
			throws ServiceException;
}
